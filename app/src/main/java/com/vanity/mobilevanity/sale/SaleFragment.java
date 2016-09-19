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
import com.vanity.mobilevanity.util.DateCalculator;

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
    DateCalculator calculator = new DateCalculator();

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

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        bannerListView.setLayoutManager(manager);

        calendarView.setLocale(Locale.KOREA);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateClicked);
                setSaleInfoView(calendar);
                setBannerListView(calendar);
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

        String endDate = calculator.CalToStr(calendar);

        SaleInfoRequest request = new SaleInfoRequest(getContext(), type, datetype, endDate);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Sale>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Sale>>> request, NetworkResult<List<Sale>> result) {
                if (result.getCode() == 1) {
                    List<Sale> sales = result.getResult();
                    events.clear();

                    for (Sale s : sales) {
                        Calendar startDay = calculator.StrToCal(s.getStartDay());
                        Calendar endDay = calculator.StrToCal(s.getEndDay());
                        Calendar temp = startDay;

                        while (temp.compareTo(endDay) <= 0) {
                            Event e = new Event(Color.RED, temp.getTimeInMillis(), s);
                            events.add(e);
                            temp.add(Calendar.DATE, 1);
                        }
                    }

                    calendarView.removeAllEvents();
                    calendarView.addEvents(events);

                    setMonthView(Calendar.getInstance());
                    setSaleInfoView(Calendar.getInstance());
                    setBannerListView(Calendar.getInstance());
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
        monthView.setText(year + "년 " + month + "월");
    }

    private void setSaleInfoView(Calendar calendar) {
        List<Event> clickedDayEvent = calendarView.getEvents(calendar.getTime());

        if (clickedDayEvent.size() == 0) {
            saleInfoView.setText(R.string.activity_cosmetic_detail_sale_guide_message);
            return;
        }

        StringBuffer buffer = new StringBuffer();

        for (Event e : clickedDayEvent) {
            Sale data = (Sale)(e.getData());

            Calendar startCal = calculator.StrToCal(data.getStartDay());
            Calendar endCal = calculator.StrToCal(data.getEndDay());

            String brandName = data.getProduct().getBrand().getName();
            String saleTitle = data.getTitle();
            String start = startCal.get(Calendar.YEAR) + "/" + startCal.get(Calendar.MONTH) + "/" + startCal.get(Calendar.DATE);
            String end = endCal.get(Calendar.YEAR) + "/" + endCal.get(Calendar.MONTH) + "/" + endCal.get(Calendar.DATE);

            buffer.append("[" + brandName + "] " + saleTitle  + " : " + start + " ~ " + end);

            if (clickedDayEvent.indexOf(e) != (clickedDayEvent.size() - 1))
                buffer.append("\n");
        }

        saleInfoView.setText(buffer);
    }

    private void setBannerListView(Calendar calendar) {
        List<Event> clickedDayEvent = calendarView.getEvents(calendar.getTime());
        saleAdapter.clear();

        for (Event e : clickedDayEvent) {
            Sale data = (Sale)(e.getData());
            saleAdapter.add(data);
        }
    }
}
