package Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import Enum.UserRole;
import java.util.Date;

public class User
{
    private int id;
    private String name;
    private String email;
    private String hashedPassword;
    private Date creationDate;
    private UserRole userRole;
    public User (){}
    public User(int id, String name, String email, String hashedPassword)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getHashedPassword()
    {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }

}
