package by.logonuk.controller.converters.deal.request;

import by.logonuk.controller.converters.user.request.UserBaseConverterRequest;
import by.logonuk.controller.requests.DealUpdateRequest;
import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import org.springframework.stereotype.Component;

@Component
public class DealUpdateConverterRequest extends DealBaseConverterRequest<DealUpdateRequest, Deal> {

    @Override
    public Deal convert(DealUpdateRequest source) {
        Deal deal = new Deal();
        deal.setId(Long.parseLong(source.getUserId()));
        return doConvert(deal, source);
    }
}
