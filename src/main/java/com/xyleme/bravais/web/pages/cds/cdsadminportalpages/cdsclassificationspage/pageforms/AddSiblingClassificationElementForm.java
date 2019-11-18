package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms;

import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Add Sibling Classification Element form.
 */
public class AddSiblingClassificationElementForm extends BaseClassificationElementFormWithCancellationOption {

    public AddSiblingClassificationElementForm(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    Button submissionButton() {
        return formButton("Add Sibling");
    }

    /**
     * Enters specified Vocab ID and Name into the respective input fields and clicks 'Add Sibling' button.
     *
     * @param vocabId - Specifies the Vocab ID
     * @param name    - Specifies the name
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage addSiblingClassificationElement(String vocabId, String name) {
        return fillOutFormWithNotEditableClassificationTypeAndClickSubmissionButton(vocabId, name);
    }
}