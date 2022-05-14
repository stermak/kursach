package com.example.kursach;

public class spisok {
    private String WarrantyName ,CompanyName , year1;


    public spisok() {}

    public spisok(String WarrantyName, String CompanyName, String year1) {
        this.WarrantyName = WarrantyName;
        this.CompanyName = CompanyName;
        this.year1 = year1;
    }

    public String getWarrantyName()
    {
        return WarrantyName;
    }
    public void setWarrantyName(String name)
    {
        this.WarrantyName = WarrantyName;
    }
    public String getCompanyName()
    {
        return CompanyName;
    }
    public void setCompanyName(String CompanyName)
    {
        this.CompanyName = CompanyName;
    }
    public String getYear1()
    {
        return year1;
    }
    public void setYear1(String year1)
    {
        this.year1 = year1;
    }
}