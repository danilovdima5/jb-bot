package kz.jussan.bot.repository;

import kz.jussan.bot.model.Request;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class RequestRepositoryImpl implements RequestRepository {

    private static final Set<Request> requests = new HashSet<>();
    private static Long COUNTER = 0L;

    @Override
    public Request saveRequest(Request request) {
        if (!request.isValid()) {
            System.out.println("Request is invalid!");
            return null;
        }
        if (requests.stream()
                .anyMatch(request1 -> Objects.equals(request1.getUserId(), request.getUserId())
                        && Objects.equals(request1.getType(), request.getType())
                        && request1.getStatus().equals(Request.Status.AWAIT))) {
            System.out.println("ActiveRequest must be completed!");
            return null;
        }
        request.setId(++COUNTER);
        request.setReceived(LocalDateTime.now());
        request.setStatus(Request.Status.AWAIT);
        requests.add(request);
        return request;
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
        if (!request.isValid()) {
            System.out.println("Request is invalid!");
            return null;
        }
        if (!requests.contains(request)) {
            System.out.println("Request with this id doesn't exist");
            return null;
        }
        requests.remove(request);
        requests.add(request);
        return request.getId();
    }

    @Override
    public List<Request> findActiveRequestByType(Request.Type type) {
        return requests.stream()
                .filter(request1 -> Objects.equals(request1.getType(), type)
                        && request1.getStatus().equals(Request.Status.AWAIT))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Request> findById(Long requestId) {
        return requests.stream().filter(request -> request.getId().equals(requestId)).findFirst();
    }
}
