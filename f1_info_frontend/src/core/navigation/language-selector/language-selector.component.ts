import {Component, Inject} from '@angular/core';
import {Language, LanguageUtil} from '../../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {cancelDialog, closeDialog, DialogResult} from '../../dialog/dialog';

interface LanguageOption {
    language: Language;
    key: string;
    flagPaths: string[];
}

@Component({
    selector: 'app-language-selector',
    templateUrl: './language-selector.component.html',
    styleUrls: ['./language-selector.component.scss'],
})
export class LanguageSelectorComponent {
    public languageOptions: LanguageOption[] = [
        {
            language: Language.ENGLISH,
            key: LanguageUtil.getLanguageTranslationKey(Language.ENGLISH),
            flagPaths: ['/assets/images/flags/gb.svg', '/assets/images/flags/us.svg'],
        },
        { language: Language.SWEDISH, key: LanguageUtil.getLanguageTranslationKey(Language.SWEDISH), flagPaths: ['/assets/images/flags/se.svg'] },
    ];

    public constructor(
        private mDialogRef: MatDialogRef<LanguageSelectorComponent>,
        private mTranslateService: TranslateService
    ) {
    }

    public languageIsSelected(language: Language): boolean {
        return this.mTranslateService.currentLang === language;
    }

    public selectLanguage(language: Language) {
        this.mTranslateService.use(language);
    }

    public cancel() {
        cancelDialog(this.mDialogRef);
    }

    public apply() {
        closeDialog(this.mDialogRef);
    }
}
