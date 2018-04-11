package org.softuni.work.areas.businesPartners.services.interfaces;

import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;

public interface BusinessPartnerService {
    void register(BusinessPartnerRegisterBindingModel bindingModel);

    void login(BusinessPartnerLoginBindingModel bindingModel) throws Exception;

}
