import {DropdownOption} from './drop-down-filter.component';

export enum RaceType {
    RACE = 'race',
    SPRINT = 'sprint',
}

export class DropDownFilterProvider {
    private static readonly FIRST_F1_SEASON: number = 1950;

    public static createSeasonOptions(): DropdownOption[] {
        const currentYear: number = new Date().getFullYear();
        const options: DropdownOption[] = [];
        for (let season = currentYear; season >= DropDownFilterProvider.FIRST_F1_SEASON; season--) {
            options.push({ displayValue: season, value: season });
        }
        return options;
    }

    public static createRaceTypeOptions(): DropdownOption[] {
        return [
            { displayValue: 'reports.driver.race', value: RaceType.RACE },
            { displayValue: 'reports.driver.sprint', value: RaceType.SPRINT },
        ];
    }
}
