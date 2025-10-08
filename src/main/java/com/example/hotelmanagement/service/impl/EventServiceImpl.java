package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelEvent;
import com.example.hotelmanagement.dao.repository.HotelEventRepository;
import com.example.hotelmanagement.enums.EventSubType;
import com.example.hotelmanagement.enums.EventType;
import com.example.hotelmanagement.model.request.EventMetricsRequest;
import com.example.hotelmanagement.service.EventService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final HotelEventRepository hotelEventRepository;

    @Override
    public Page<HotelEvent> list(int page, int pageSize) {
        // Spring Data JPA page numbers are 0-based.
        int pageNumber = Math.max(page - 1, 0);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return hotelEventRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return hotelEventRepository.count();
    }

    @Override
    public void recordEvent(EventType type, EventSubType subType, String content) {
        HotelEvent event = new HotelEvent();
        event.setEventType(type.getValue());
        event.setEventSubType(subType.getValue());
        event.setEventContent(content);
        event.setEventLevel("info"); // Default level
        hotelEventRepository.save(event);
    }

    @Override
    public long getMetrics(EventMetricsRequest request) {
        if (!StringUtils.hasText(request.getEventType())) {
            return 0;
        }

        Specification<HotelEvent> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // eventType is mandatory
            predicates.add(criteriaBuilder.equal(root.get("eventType"), request.getEventType()));

            // eventSubType is optional
            if (StringUtils.hasText(request.getEventSubType())) {
                predicates.add(criteriaBuilder.equal(root.get("eventSubType"), request.getEventSubType()));
            }

            // timeRange filter
            if ("today".equalsIgnoreCase(request.getTimeRange())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), LocalDate.now().atStartOfDay()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return hotelEventRepository.count(spec);
    }
}
