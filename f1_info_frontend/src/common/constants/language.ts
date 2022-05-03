export enum Language {
    ENGLISH = 'en',
    SWEDISH = 'sv',
}

export class LanguageUtil {
    public static getLanguageTranslationKey(language: Language): string {
        if (language === Language.ENGLISH) {
            return 'language.english';
        }
        return 'language.swedish';
    }
}
