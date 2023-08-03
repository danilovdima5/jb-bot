package kz.jussan.bot.service;

import kz.jussan.bot.model.ClimateControlAction;
import kz.jussan.bot.model.ClimateControlRequest;
import kz.jussan.bot.model.Request;
import kz.jussan.bot.repository.RequestRepository;
import kz.jussan.bot.repository.RequestRepositoryImpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClimateControlServiceImpl implements ClimateControlService{

    private static final Map<String, List<Request>> REQUESTS = new HashMap<>();
    private final RequestRepository requestRepository = new RequestRepositoryImpl();
    private static final Integer REQUEST_PROCESS_DELAY_MINUTES = 5;

    @Override
    public String registerRequest(Request request) {
        if (!request.isValid()
                || !Request.Type.CLIMATE_CONTROL.equals(request.getType())
                || Objects.isNull(request.getPayload())
                || !(request.getPayload() instanceof ClimateControlRequest)) {
            return "Invalid request";
        }
        Request result = requestRepository.saveRequest(request);
        if (Objects.isNull(result)) {
            return "Couldn't save request";
        }
        ClimateControlRequest payload = (ClimateControlRequest) result.getPayload();
        if (!REQUESTS.containsKey(payload.getZone())) {
            REQUESTS.put(payload.getZone(), List.of(result));
        } else {
            REQUESTS.get(payload.getZone()).add(result);
        }
        return "Successfully registered request";
    }

    @Override
    public List<Request> showRequests(String userId) {
        return requestRepository.findActiveRequestsByUserId(userId);
    }

    @Override
    public String cancelRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (Objects.isNull(request)) {
            return "Request with this id not found!";
        }
        if (!request.getStatus().equals(Request.Status.AWAIT)) {
            return "Request with this status can't be canceled";
        }
        request.setStatus(Request.Status.DECLINED);
        Request result = requestRepository.saveRequest(request);

        return Objects.nonNull(result) ? "ok" : "error";
    }

    @Override
    public List<ClimateControlRequest> checkActiveRequests() {
  /*      REQUESTS.entrySet().stream()
                .filter(stringListEntry -> stringListEntry.getValue().stream()
                        .filter(request -> LocalDateTime.now().minusMinutes(REQUEST_PROCESS_DELAY_MINUTES)
                                .isAfter(request.getReceived()))
                        .findAny().isPresent();*/
        return null;
    }

    private ClimateControlRequest makeDecision(List<Request> requests) {
        Map<ClimateControlAction, Long> requestedActions = requests.stream()
                .filter(request -> Request.Type.CLIMATE_CONTROL.equals(request.getType()))
                .map(request -> (ClimateControlRequest) request.getPayload())
                .collect(Collectors.groupingBy(ClimateControlRequest::getAction, Collectors.counting()));
        return null;
    }
}
