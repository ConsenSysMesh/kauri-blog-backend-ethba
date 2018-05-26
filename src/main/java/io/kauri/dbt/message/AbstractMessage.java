package io.kauri.dbt.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMessage<D> implements Message<D> {

    String type;

    D details;
}
