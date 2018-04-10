package dgsw.hs.kr.ahnt.Network.Response;

/**
 * Created by neutral on 10/04/2018.
 */

public class LoginResponse {
    private String message;

    private String status;

    private LoginData data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public LoginData getData ()
    {
        return data;
    }

    public void setData (LoginData data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", data = "+data+"]";
    }
}
