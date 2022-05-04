import {Component, Inject} from '@angular/core';
import {Language, LanguageUtil} from '../../../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

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
    private readonly mOpenedSelectedLanguage: Language;

    public constructor(
        private dialogRef: MatDialogRef<LanguageSelectorComponent>,
        private mTranslateService: TranslateService
    ) {
        this.mOpenedSelectedLanguage = mTranslateService.currentLang as Language;
    }

    public languageIsSelected(language: Language): boolean {
        return this.mTranslateService.currentLang === language;
    }

    public selectLanguage(language: Language) {
        this.mTranslateService.use(language);
    }

    public cancel() {
        this.selectLanguage(this.mOpenedSelectedLanguage);
        this.dialogRef.close();
    }
}
