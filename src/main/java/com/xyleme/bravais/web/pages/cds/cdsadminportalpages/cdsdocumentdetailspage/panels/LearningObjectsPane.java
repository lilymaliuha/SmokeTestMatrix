package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsdocumentdetailspage.panels;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.BaseCDSPanelOnDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of Learning Objects pane on CDS Document Details page.
 */
public class LearningObjectsPane extends BaseCDSPanelOnDetailsPage {

    public LearningObjectsPane(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public LearningObjectsPane load() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return panelBody().isAvailable();
    }

    private Element learningObjectTreePanel() {
        return new Element(driver, By.xpath("//div[contains(@class, 'learning-object-tree-panel')]"));
    }

    private Element panelBody() {
        return new Element(driver, By.xpath(learningObjectTreePanel().getXpathLocatorValue() +
                "/div[@class='panel-body']"));
    }

    private Element parentNodeElement(String nodeName) {
        return new Element(driver, By.xpath("//div[@class='content-object-tree']/ul/li/" +
                "span[starts-with(@class, 'tree-node') and text()='" + nodeName + "']"));
    }

    /**
     * Checks if specified parent node is available in the pane body.
     *
     * @param nodeName - Specifies name of the parent node intended to be checked
     * @return {@code boolean}
     */
    public boolean isParentNodeAvailable(String nodeName) {
        return parentNodeElement(nodeName).isAvailable();
    }

    /**
     * Gets list of web elements which represent child nodes of the specified parent node.
     *
     * @param parentNodeName - Specifies name of the parent node
     * @return {@code List<WebElement>}
     */
    private List<WebElement> getChildNodesOfParentNode(String parentNodeName) {
        return getElementsDynamically(By.xpath(parentNodeElement(parentNodeName).getXpathLocatorValue() +
                "/following-sibling::div//span[starts-with(@class, 'tree-node')]"));
    }

    /**
     * Gets number of child nodes of the specified parent node.
     *
     * @param parentNodeName - Specifies name of the parent node
     * @return {@code int}
     */
    public int getNumberOfChildNodesOfParentNode(String parentNodeName) {
        return getChildNodesOfParentNode(parentNodeName).size();
    }

    /**
     * Gets name of a specific child node of the specified parent node.
     *
     * @param parentNodeName - Specifies name of the parent node
     * @param childNodeIndex - Specifies index of the child node name of which is intended to be retrieved
     * @return {@code String}
     */
    public String getNameOfChildNode(String parentNodeName, int childNodeIndex) {
        return getChildNodesOfParentNode(parentNodeName).get(childNodeIndex).getText();
    }
}