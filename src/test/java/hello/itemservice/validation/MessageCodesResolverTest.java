package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");

        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        // messageCode = required.item
        // messageCode = required 순으로 나온다.
        // 위의 코드 == new ObjectError("item", new String[]{"required.item","required"}) 와 동일한 역할을 함.

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item", "itemName", String.class);
        assertThat(messageCodes).containsExactly("required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required");
    }

    // bindingResult.rejectValue("itemName","required"); 여기 안에 위의 codesResolver 가 들어있어서 자동으로 처리해준다. 차례대로 찾아줌
    // new FieldError("item","itemName",null,false,messageCodes,null,null);
}
