package io.kauri.dbt.message;

public interface Message<D> {

    String getType();

    D getDetails();
}
