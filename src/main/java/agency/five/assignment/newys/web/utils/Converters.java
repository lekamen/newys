package agency.five.assignment.newys.web.utils;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class Converters {

    public static final Converter<String, byte[]> STRING_TO_BYTE_ARRAY_CONVERTER = new Converter<String, byte[]>() {
        @Override
        public Result<byte[]> convertToModel(String s, ValueContext valueContext) {
            return StringUtils.isNotEmpty(s) ? Result.ok(s.getBytes(StandardCharsets.UTF_8)): Result.error(MsgUtil.get("converter.string.to.byteArray.required"));
        }

        @Override
        public String convertToPresentation(byte[] bytes, ValueContext valueContext) {
            return bytes != null ? ArticleUtil.getReadableContent(bytes) : "";
        }
    };
}
