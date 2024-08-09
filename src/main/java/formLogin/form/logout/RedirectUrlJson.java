package formLogin.form.logout;

import lombok.Getter;

@Getter
public class RedirectUrlJson {

    private final String redirectUrl;


    public RedirectUrlJson(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


}
