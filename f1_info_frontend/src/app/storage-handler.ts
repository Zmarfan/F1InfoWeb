import {Language} from '../common/constants/language';

interface StorageConfig {
    csrfToken: string;
    language: Language;
    isDarkMode: boolean;
}

export class StorageHandler {
    private static readonly CONFIG_KEY = 'conf';
    private static readonly DEFAULT_CONFIG: StorageConfig = {
        language: Language.ENGLISH,
        csrfToken: '',
        isDarkMode: false,
    };

    public static getConfig(): StorageConfig {
        try {
            return JSON.parse(localStorage.getItem(StorageHandler.CONFIG_KEY) ?? '');
        } catch (e) {
            return StorageHandler.DEFAULT_CONFIG;
        }
    }

    public static modifyConfig(modifier: (config: StorageConfig) => StorageConfig) {
        const newConfig = modifier(StorageHandler.getConfig());
        localStorage.setItem(StorageHandler.CONFIG_KEY, JSON.stringify(newConfig));
    }
}
