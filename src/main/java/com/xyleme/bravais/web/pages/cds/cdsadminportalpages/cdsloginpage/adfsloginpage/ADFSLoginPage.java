package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsloginpage.adfsloginpage;

public interface ADFSLoginPage {

    /**
     * Fills out Sign In form with valid data (username and password) and clicks 'Sign in' button.
     *
     * @param username - Specifies the username intended to be entered
     * @param password - Specifies the password intended to be entered
     */
    void fillOutAndSubmitLoginFormWithValidData(String username, String password);
}