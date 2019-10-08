package com.gama.mutamer.interfaces;


import com.gama.mutamer.viewModels.webServices.ValidationResult;

public interface IViewModel {
    ValidationResult validate();

    String serviceFormat();
}
