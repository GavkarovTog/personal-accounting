package com.example.personal_accounting.operation.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.operation.dto.OperationCreationForm;
import com.example.personal_accounting.operation.dto.OperationEditionForm;
import com.example.personal_accounting.shared_entities.OperationMemberService;

public class OperationValidator implements Validator {
    @Autowired
    private OperationMemberService operationMemberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(OperationCreationForm.class) || clazz.equals(OperationEditionForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null || ! supports(target.getClass())) {
            return;
        }
        
        if (target instanceof OperationCreationForm creationForm) {
            validateEntitiesExistAndNotTheSame(creationForm.from, creationForm.to, errors);
        } else if (target instanceof OperationEditionForm editionForm) {
            validateEntitiesExistAndNotTheSame(editionForm.from, editionForm.to, errors);
        }
    }

    private void validateEntitiesExistAndNotTheSame(Long fromId, Long toId, Errors errors) {
        if (fromId != null && ! operationMemberService.getOperationMember(fromId).isPresent()) {
            throw new UnsupportedOperationException("Unable to work with non-existing operation member");
        }

        if (toId != null && ! operationMemberService.getOperationMember(toId).isPresent()) {
            throw new UnsupportedOperationException("Unable to work with non-existing operation member");
        }

        if (fromId != null && fromId.equals(toId)) {
            errors.rejectValue("from", "op.trns.eq.accs");
            errors.rejectValue("to", "op.trns.eq.accs");
        }
    }
}