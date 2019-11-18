package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.pageforms;

import com.xyleme.bravais.datacontainers.ClassificationType;
import com.xyleme.bravais.web.elements.Button;
import com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdsclassificationspage.CDSClassificationsPage;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of Edit Classification Element form.
 */
public class EditClassificationElementForm extends BaseClassificationElementFormWithCancellationOption {

    public EditClassificationElementForm(WebDriver driver) {
        super(driver);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    Button submissionButton() {
        return formButton("Update");
    }

    /**
     * Enters specified Vocab ID and Name into the respective input fields, selects specified classification type in the
     * respective drop-down and clicks 'Update' button.
     *
     * @param vocabId - Specifies the Vocab ID
     * @param name    - Specifies the name
     *
     * @return {@code CDSClassificationsPage}
     */
    public CDSClassificationsPage editClassificationElement(String vocabId,
                                                            String name, ClassificationType classificationType) {
        return fillOutFormAndClickSubmissionButton(vocabId, name, classificationType);
    }
}