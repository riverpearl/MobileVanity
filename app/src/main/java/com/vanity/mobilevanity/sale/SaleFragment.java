package com.vanity.mobilevanity.sale;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.SaleAdapter;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Sale;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.SaleInfoRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends Fragment {

    public SaleFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.text_month)
    TextView monthView;

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView calendarView;

    @BindView(R.id.text_sale_info)
    TextView saleInfoView;

    @BindView(R.id.rv_sale_banner)
    RecyclerView bannerListView;

    SaleAdapter saleAdapter;
    List<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);

        saleAdapter = new SaleAdapter();
        saleAdapter.setOnAdapterItemClickListener(new SaleAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Sale data, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getLink()));
                startActivity(intent);
            }
        });
        bannerListView.setAdapter(saleAdapter);

        //StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        bannerListView.setLayoutManager(manager);

        calendarView.setLocale(Locale.KOREA);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSaleInfoView(dateClicked);
                setBannerListView(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(firstDayOfNewMonth);
                setMonthView(calendar);
            }
        });

        setCalendarView();

        return view;
    }

    private void setCalendarView() {
        String type = SaleInfoRequest.TAG_TYPE_DATE;
        String datetype = SaleInfoRequest.TAG_TYPE_END_DATE;

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");
        String parseDate = form.format(calendar.getTime());

        SaleInfoRequest request = new SaleInfoRequest(getContext(), type, datetype, parseDate);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Sale>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Sale>>> request, NetworkResult<List<Sale>> result) {
                if (result.getCode() == 1) {
                    List<Sale> sales = result.getResult();
                    events.clear();

                    for (int i = 0; i < sales.size(); i++) {
                        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");

                        try {
                            Date startDay = form.parse(sales.get(i).getStartDay());
                            Date endDay = form.parse(sales.get(i).getEndDay());
                            Date temp = startDay;

                            while (temp.compareTo(endDay) <= 0) {
                                long millis = temp.getTime();

                                Event e = new Event(Color.RED, millis, sales.get(i));
                                events.add(e);

                                Calendar tomorrow = Calendar.getInstance();
                                tomorrow.setTime(temp);
                                tomorrow.add(Calendar.DATE, 1);

                                temp = tomorrow.getTime();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    calendarView.removeAllEvents();
                    calendarView.addEvents(events);
                    setMonthView(Calendar.getInstance());
                    setSaleInfoView(Calendar.getInstance().getTime());
                    setBannerListView(Calendar.getInstance().getTime());
                }

            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Sale>>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    private void setMonthView(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        monthView.setText(year + "년 " + month + "월의 세일정보");
    }

    private void setSaleInfoView(Date date) {
        List<Event> clickedDayEvent = calendarView.getEvents(date);
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");

        for (int i = 0; i < clickedDayEvent.size(); i++) {
            Sale data = (Sale)(clickedDayEvent.get(i).getData());

            try {
                Date startDay = form.parse(data.getStartDay());
                Date endDay = form.parse(data.getEndDay());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(startDay);

                Calendar endCal = Calendar.getInstance();
                endCal.setTime(endDay);

                String brandName = data.getProduct().getBrand().getName();
                String start = startCal.get(Calendar.YEAR) + "/" + startCal.get(Calendar.MONTH) + "/" + startCal.get(Calendar.DATE);
                String end = endCal.get(Calendar.YEAR) + "/" + endCal.get(Calendar.MONTH) + "/" + endCal.get(Calendar.DATE);

                buffer.append(brandName + " 세일 : " + start + " ~ " + end + "\n");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        saleInfoView.setText(buffer);
    }

    private void setBannerListView(Date date) {
        List<Event> clickedDayEvent = calendarView.getEvents(date);
        saleAdapter.clear();

        for (int i = 0; i < clickedDayEvent.size(); i++) {
            Sale data = (Sale)(clickedDayEvent.get(i).getData());
            saleAdapter.add(data);
        }
    }
}
