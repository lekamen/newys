package agency.five.assignment.newys.core.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum AuthorStatusEnum {
    NONE(0),
    ACCEPTED(1),
    REQUESTED(2),
    DECLINED(3);

    private Integer value;

    AuthorStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static AuthorStatusEnum fromValue(Integer value) {
        for (AuthorStatusEnum status : AuthorStatusEnum.values()) {
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
    public static class AuthorStatusEnumConverter implements AttributeConverter<AuthorStatusEnum, Integer> {

        @Override
        public Integer convertToDatabaseColumn(AuthorStatusEnum enumeration) {
            return enumeration != null ? enumeration.getValue() : null;
        }

        @Override
        public AuthorStatusEnum convertToEntityAttribute(Integer value) {
            return AuthorStatusEnum.fromValue(value);
        }
    }
}
