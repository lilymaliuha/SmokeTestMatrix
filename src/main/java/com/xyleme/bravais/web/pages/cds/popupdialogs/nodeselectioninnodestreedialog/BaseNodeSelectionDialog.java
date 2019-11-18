package com.xyleme.bravais.web.pages.cds.popupdialogs.nodeselectioninnodestreedialog;

import com.xyleme.bravais.web.WebPage;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of a base Node Selection In Nodes Tree dialog (common elements and interaction methods for
 * "Move Document to..." and "Manage Classifications" dialogs).
 */
public abstract class BaseNodeSelectionDialog extends WebPage<BaseNodeSelectionDialog> {

    public BaseNodeSelectionDialog(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAvailable() {
        return dialogHeaderElement().isAvailable() &&
                dialogBodyElement().isAvailable() &&
                cancelButton().isAvailable();
    }

    protected abstract Element dialogParentElement();

    private Element dialogHeaderElement() {
        return new Element(driver, By.xpath(dialogParentElement().getXpathLocatorValue() +
                "//div[starts-with(@class, 'modal-header')]"));
    }

    private Element dialogBodyElement() {
        return new Element(driver, By.xpath(dialogParentElement().getXpathLocatorValue() +
                "//div[starts-with(@class, 'modal-body')]"));
    }

    private Element dialogFooterElement() {
        return new Element(driver, By.xpath(dialogParentElement().getXpathLocatorValue() +
                "//div[starts-with(@class, 'modal-footer')]"));
    }

    protected Button dialogFooterButton(String buttonName) {
        return new Button(driver, By.xpath(dialogFooterElement().getXpathLocatorValue() + "/button[text()='" + buttonName
                + "']"));
    }

    private Button cancelButton() {
        return dialogFooterButton("Cancel");
    }

    private Element nodeInNodesTree(String nodeName) {
        return new Element(driver, By.xpath("//span[contains(@class, 'tree-node') and (text()='" + nodeName + "')]"));
    }

    private Element containerOfExpandedNode(String nodeName) {
        return new Element(driver, By.xpath(nodeInNodesTree(nodeName).getXpathLocatorValue() + "/parent::li"));
    }

    private Element childNodeOfParentNode(String parentNodeName, String childNodeName) {
        return new Element(driver, By.xpath(containerOfExpandedNode(parentNodeName).getXpathLocatorValue() +
                nodeInNodesTree(childNodeName).getXpathLocatorValue()));
    }

    private Element nodeExpandCollapseIcon(String nodeName) {
        return new Element(driver, By.xpath(nodeInNodesTree(nodeName).getXpathLocatorValue() +
                "/preceding-sibling::i[not(contains(@class, 'hide'))]"));
    }

    /**
     * Checks if specified node is collapsed in the nodes tree.
     *
     * @param nodeName - Specifies name of the node intended to be checked
     * @return {@code boolean}
     */
    private boolean isNodeCollapsed(String nodeName) {
        return nodeExpandCollapseIcon(nodeName).waitUntilAvailable().getAttribute("class").contains("collapsed");
    }

    /**
     * Selects specified target node.
     *
     * @param targetNodePath - Specifies path to the target node
     * @param dialogType     - Specifies type of a dialog expected to be returned
     * @return {@code <T extends BaseNodeSelectionDialog>}
     */
    protected <T extends BaseNodeSelectionDialog> T selectTargetNode(String targetNodePath, Class<T> dialogType) {
        List<String> nodes = new ArrayList<>(Arrays.asList((targetNodePath).split("\\|")));
        String targetNode = nodes.get(nodes.size() - 1);
        nodes.remove(targetNode);

        for (String node : nodes) {
            if (isNodeCollapsed(node)) {
                nodeExpandCollapseIcon(node).waitUntilAvailable().click();
            }
        }

        childNodeOfParentNode(nodes.get(nodes.size() - 1), targetNode).waitUntilAvailable().click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.attributeContains(
                By.xpath(nodeInNodesTree(targetNode).getXpathLocatorValue()), "class", "selected"));
        return constructClassInstance(dialogType);
    }
}