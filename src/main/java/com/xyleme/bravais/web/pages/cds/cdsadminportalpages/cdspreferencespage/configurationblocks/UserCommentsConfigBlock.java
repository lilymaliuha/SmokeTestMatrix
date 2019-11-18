package com.xyleme.bravais.web.pages.cds.cdsadminportalpages.cdspreferencespage.configurationblocks;

import com.xyleme.bravais.datacontainers.configparametersofpreferencespage.usercommentsconfigs.CommentingPermissionParameter;
import com.xyleme.bravais.datacontainers.configparametersofpreferencespage.usercommentsconfigs.SendEmailConfigParameter;
import com.xyleme.bravais.datacontainers.configparametersofpreferencespage.YesNoParameter;
import com.xyleme.bravais.web.elements.Element;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of User Comments configuration block.
 */
public class UserCommentsConfigBlock extends BaseConfigBlock {

    public UserCommentsConfigBlock(WebDriver driver, Element blockElement) {
        super(driver, blockElement);
        this.waitUntilAvailable();
    }

    @Override
    public boolean isAvailable() {
        if (configurationItemCheckbox("Enable User Comments", "Yes").isSelected()) {
            return enableUserCommentsConfigItemContainerBody().isAvailable() &&
                    allowUsersToEditAndDeleteTheirCommentsConfigItemContainerBody().isAvailable() &&
                    sendEmailNotificationsConfigItemContainerBody().isAvailable() &&
                    minimumPermissionsToCommentDocumentConfigItemContainerBody().isAvailable();
        } else {
            return enableUserCommentsConfigItemContainerBody().isAvailable();
        }
    }

    private Element enableUserCommentsConfigItemContainerBody() {
        return configurationItemContainerBody("Enable User Comments");
    }

    private Element allowUsersToEditAndDeleteTheirCommentsConfigItemContainerBody() {
        return configurationItemContainerBody("Allow Users to Edit and Delete their own comments?");
    }

    private Element sendEmailNotificationsConfigItemContainerBody() {
        return configurationItemContainerBody(
                "Send e-mail notifications when a comment is posted or edited to:");
    }

    private Element minimumPermissionsToCommentDocumentConfigItemContainerBody() {
        return configurationItemContainerBody("Minimum permissions to comment a document");
    }

    /**
     * Sets specified configuration (checks or unchecks the specified checkbox).
     *
     * @param configuration - Specifies name (title) of the configuration
     * @param parameter     - Specifies configuration parameter intended to be set
     * @param check         - Specifies the decision whether to check or uncheck the specified checkbox
     * @param save          - Specifies the decision whether to save the changes or not (clicks 'Save' button in case of
     *                      'true')
     * @return {@code UserCommentsConfigBlock}
     */
    UserCommentsConfigBlock setConfiguration(String configuration, String parameter, boolean check, boolean save) {
        if (check) {
            configurationItemCheckbox(configuration, parameter).select();
        } else {
            configurationItemCheckbox(configuration, parameter).unselect();
        }
        if (save) {
            clickSaveButton();
        }
        return this;
    }

    /**
     * Sets specified parameter of "Enable User Comments" configuration (selects respective checkbox).
     *
     * @param parameter - Specifies the configuration parameter (checkbox) intended to be set
     * @return {@code UserCommentsConfigBlock}
     */
    public UserCommentsConfigBlock enableUserComments(YesNoParameter parameter, boolean save) {
        return setConfiguration("Enable User Comments", parameter.getValue(), true, save);
    }

    /**
     * Sets specified parameter of "Allow Users to Edit and Delete their own comments?" configuration (selects
     * respective checkbox).
     *
     * @param parameter - Specifies the configuration parameter (checkbox) intended to be set
     * @return {@code UserCommentsConfigBlock}
     */
    public UserCommentsConfigBlock allowUsersToEditAndDeleteTheirComments(YesNoParameter parameter, boolean save) {
        return setConfiguration("Allow Users to Edit and Delete their own comments?", parameter.getValue(),
                true, save);
    }

    /**
     * Sets specified parameter of "Send e-mail notifications when a comment is posted or edited to:" configuration
     * (selects respective checkbox).
     *
     * @param parameter - Specifies the configuration parameter (checkbox) intended to be set
     * @param check     - Specifies the decision whether to check the checkbox or uncheck (unchecks in case of 'false')
     * @return {@code UserCommentsConfigBlock}
     */
    public UserCommentsConfigBlock sendEmailNotificationsWhenCommentIsPostedOrEditedTo(SendEmailConfigParameter parameter,
                                                                                       boolean check, boolean save) {
        return setConfiguration("Send e-mail notifications when a comment is posted or edited to:",
                parameter.getValue(), check, save);
    }

    /**
     * Sets specified parameter of "Minimum permissions to comment a document" configuration (selects respective checkbox).
     *
     * @param parameter - Specifies the configuration parameter (checkbox) intended to be set
     * @return {@code UserCommentsConfigBlock}
     */
    public UserCommentsConfigBlock minimumPermissionsToCommentADocument(CommentingPermissionParameter parameter, boolean save) {
        return setConfiguration("Minimum permissions to comment a document", parameter.getValue(),
                true, save);
    }
}