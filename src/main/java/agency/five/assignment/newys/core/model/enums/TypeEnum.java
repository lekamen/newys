package agency.five.assignment.newys.core.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TypeEnum {
    NEWS(0),
    OTHER(1);


    private Integer value;

    TypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static TypeEnum fromValue(Integer value) {
        for (TypeEnum status : TypeEnum.values()) {
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
    public static class TypeEnumConverter implements AttributeConverter<TypeEnum, Integer> {

        @Override
        public Integer convertToDatabaseColumn(TypeEnum enumeration) {
            return enumeration != null ? enumeration.getValue() : null;
        }

        @Override
        public TypeEnum convertToEntityAttribute(Integer value) {
            return TypeEnum.fromValue(value);
        }
    }
}
