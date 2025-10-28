package org.Library.BLL.Services.Interfaces;

public interface IFineService {
    public int payFineByUserName(String name,int amount);

    public String getErrorMessage();
}
