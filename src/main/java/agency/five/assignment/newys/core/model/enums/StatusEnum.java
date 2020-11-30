package agency.five.assignment.newys.core.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum StatusEnum {
    INACTIVE(0),
    ACTIVE(1);

    private Integer value;

    StatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static StatusEnum fromValue(Integer value) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        return null;
    }

    /**
     * JPA converter enuma u vrijednost i obrnuto. Spring boot automatski pronađe i registrira klasu
     */
    @Converter(autoApply = true)
    @SuppressWarnings("unused")
    public static class StatusEnumConverter implements AttributeConverter<StatusEnum, Integer> {

        @Override
        public Integer convertToDatabaseColumn(StatusEnum enumeration) {
            return enumeration != null ? enumeration.getValue() : null;
        }

        @Override
        public StatusEnum convertToEntityAttribute(Integer value) {
            return StatusEnum.fromValue(value);
        }
    }
}
