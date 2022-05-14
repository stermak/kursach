package com.example.kursach;

public class spisok {
    private String WarrantyName ,CompanyName , year;


    public spisok() {}

    public spisok(String WarrantyName, String CompanyName, String year) {
        this.WarrantyName = WarrantyName;
        this.CompanyName = CompanyName;
        this.year = year;
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
    public String getYear()
    {
        return year;
    }
    public void setYear(String year)
    {
        this.year = year;
    }
}