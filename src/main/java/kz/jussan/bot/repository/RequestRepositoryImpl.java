package kz.jussan.bot.repository;

import kz.jussan.bot.model.Request;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class RequestRepositoryImpl implements RequestRepository {

    private final Set<Request> requests = new HashSet<>();
    private static Long COUNTER = 0L;

    @Override
    public Long saveRequest(Request request) {
        if (!request.isValid()) {
            System.out.println("Request is invalid!");
            return null;
        }
        if (requests.stream()
                .filter(request1 -> Objects.equals(request1.getUserId(), request.getUserId())
                        && Objects.equals(request1.getType(), request.getType())
                        && request1.getStatus().equals(Request.Status.AWAIT))
                .findAny()
                .isPresent()) {
            System.out.println("ActiveRequest must be completed!");
            return null;
        }
        request.setId(++COUNTER);
        request.setReceived(LocalDateTime.now());
        request.setStatus(Request.Status.AWAIT);
        requests.add(request);
        return request.getId();
    }

    @Override
    public List<Request> findActiveRequestsByUserId(String userId) {
        return requests.stream()
                .filter(request1 -> Objects.equals(request1.getUserId(), userId)
                        && request1.getStatus().equals(Request.Status.AWAIT))
                .collect(Collectors.toList());
    }

    @Override
    public Long updateRequest(Request request) {
        return null;
    }

    @Override
    public List<Request> findActiveRequestByType(Request.Type type) {
        return null;
    }
}
