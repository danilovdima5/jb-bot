package kz.jussan.bot.service;

import kz.jussan.bot.model.ClimateControlRequest;
import kz.jussan.bot.model.Request;
import kz.jussan.bot.repository.RequestRepository;
import kz.jussan.bot.repository.RequestRepositoryImpl;

import java.util.*;

import static java.util.Objects.nonNull;
import static jdk.dynalink.linker.support.Guards.isNull;

public class ClimateControlServiceImpl implements ClimateControlService{

    private static final Map<String, List<Request>> REQUESTS = new HashMap<>();
    private final RequestRepository requestRepository = new RequestRepositoryImpl();

    @Override
    public String registerRequest(Request request) {
        if (!request.isValid()
                || !Request.Type.CLIMATE_CONTROL.equals(request.getType())
                || Objects.isNull(request.getPayload())
                || !(request.getPayload() instanceof ClimateControlRequest)) {
            return "Invalid request";
        }
        Long requestId = requestRepository.saveRequest(request);
        if (Objects.isNull(requestId)) {
            return "Couldn't save request";
        }
        ClimateControlRequest payload = (ClimateControlRequest) request.getPayload();
        if (!REQUESTS.containsKey(payload.getZone())) {
            REQUESTS.put(payload.getZone(), List.of(request));
        } else {
            REQUESTS.get(payload.getZone()).add(request);
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
        Long id = requestRepository.saveRequest(request);

        return Objects.nonNull(id) ? "ok" : "error";
    }
}
