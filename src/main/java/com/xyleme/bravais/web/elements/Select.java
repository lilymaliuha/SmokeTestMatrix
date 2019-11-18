package com.xyleme.bravais.web.elements;

import com.xyleme.bravais.web.WebComponent;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class Select extends WebComponent<Select> {

    private String xpath;
    public Select(WebDriver driver, By findByMethod) {
        super(driver, findByMethod);
        xpath = (findByMethod.toString()).replace("By.xpath: ","");
    }

    public Select selectItem(String item) {
        WebElement select = this.getWebElement();
        for (WebElement element : select.findElements(By
                .tagName("option"))) {
            if (element.getText().equals(item)) {
                element.click();
            }
        }
        if (xpath.contains("form")){
        select.sendKeys(Keys.ENTER);}
        return this;
    }

    public Select selectItemByValue(String item) {
        WebElement selectElem = this.getWebElement();
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(selectElem);
        select.selectByVisibleText(item);
        return this;
    }

    /**
     * Gets list of available drop-down options.
     *
     * @return {@code List<String>}
     */
    public List<String> getListOfOptions() {
        List<String> optionsToReturn = new ArrayList<>();
        List<WebElement> options = new org.openqa.selenium.support.ui.Select(this.getWebElement()).getOptions();
        options.forEach(option -> optionsToReturn.add(option.getText()));
        return optionsToReturn;
    }

    public Boolean contains(String item){
		Boolean p = false;
		WebElement select = this.getWebElement();
		for (WebElement element : select.findElements(By
				.tagName("option"))) {
			if (element.getText().equals(item)) {
				p = true;
			}
		}
		return p;
	}
}
