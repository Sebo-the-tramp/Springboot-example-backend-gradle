package it.refill.backend.payload.request.list;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

public class ListPayload {

    @NotBlank
    private String name;

    @Nullable
    private String description;

    private boolean isCart;


    public ListPayload() {
    }

    public ListPayload(String name, String description, boolean isCart) {
        this.name = name;
        this.description = description;
        this.isCart = isCart;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsCart() {
        return this.isCart;
    }

    public boolean getIsCart() {
        return this.isCart;
    }

    public void setIsCart(boolean isCart) {
        this.isCart = isCart;
    }

    public ListPayload name(String name) {
        this.name = name;
        return this;
    }

    public ListPayload description(String description) {
        this.description = description;
        return this;
    }

    public ListPayload isCart(boolean isCart) {
        this.isCart = isCart;
        return this;
    }
    
}