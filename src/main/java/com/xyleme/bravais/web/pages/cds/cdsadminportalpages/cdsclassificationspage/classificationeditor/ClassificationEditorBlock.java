package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.classificationeditor;

import com.xyleme.bravais.web.elements.Element;
import com.xyleme.bravais.web.elements.Select;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Classification Editor Block available on Classifications page when at least one classification is
 * available on the page.
 */
public class ClassificationEditorBlock extends CDSClassificationsPage {

    public ClassificationEditorBlock(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    public ClassificationEditorBlock(WebDriver driver, boolean waitUntilAvailable) {
        super(driver);
        if (waitUntilAvailable) {
            this.waitUntilAvailable();
        }
    }

    @Override
    public boolean isAvailable() {
        return editorBlockBody().isAvailable();
    }

    private Element editorBlockBody() {
        return new Element(driver, By.xpath("//div[@class='taxonEditor bloc']"));
    }

    /**
     * Gets specified editing section.
     *
     * @param sectionIndex - Specifies the section index.
     * @return {@code Select}
     */
    private Select getEditingSection(int sectionIndex) {
        String hierarchyUnit = "_";
        String hierarchyLevel = hierarchyUnit;
        if (sectionIndex > 0) {
            for (int i = 0; i < sectionIndex; i++) {
                hierarchyLevel = hierarchyLevel + hierarchyUnit;
            }
        }
        return new Select(driver, By.xpath("//div[@class='taxonEditor bloc']//select[@name='taxonOptions" + hierarchyLevel
                + "']"));
    }

    /**
     * Gets list of classification elements available in the specified editor section.
     *
     * @param sectionIndex - Specifies index of the editor section options of which are intended to be returned
     * @return {@code List<String>}
     */
    public List<String> getAvailableClassificationElements(int sectionIndex) {
        return getEditingSection(sectionIndex).getListOfOptions();
    }

    /**
     * Selects specified classification node.
     *
     * @param nodePath - Specifies path to the node in the following format: nodeA|nodeAA|nodeAAA
     * @return {@code ClassificationEditorToolbar}
     */
    public ClassificationEditorToolbar selectClassificationNode(String nodePath) {
        List<String> nodes = new ArrayList<>(Arrays.asList((nodePath).split("\\|")));

        for (String node : nodes) {
            int index = nodes.indexOf(node);
            getEditingSection(index).waitUntilAvailable().selectItemByValue(node);
        }

        return new ClassificationEditorToolbar(driver);
    }
}