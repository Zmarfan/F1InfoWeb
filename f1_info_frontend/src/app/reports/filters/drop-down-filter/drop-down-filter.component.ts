import {Component, Input, OnChanges} from '@angular/core';
import {LoadingSpinnerSize} from '../../../../core/loading/loading-element/loading-element.component';

export interface DropdownOption {
    displayValue: string | number;
    value: string | number | null;
    translateParams?: any;
}

@Component({
    selector: 'app-drop-down-filter',
    templateUrl: './drop-down-filter.component.html',
    styleUrls: ['./drop-down-filter.component.scss'],
})
export class DropDownFilterComponent implements OnChanges {
    @Input() public loading: boolean = false;
    @Input() public options: DropdownOption[] = [];
    @Input() public allOption: boolean = true;
    @Input() public labelKey: string = '';
    @Input() public selectedValue: string | number | null = null;
    @Input() public valueChanged!: (value: any) => void;

    public loadingSpinnerSize: LoadingSpinnerSize = LoadingSpinnerSize.SMALL;
    public selectedOption!: string | number | null;
    public uniqueLabel: string = `dropDownFilter${Math.random()}`;

    public ngOnChanges() {
        if (this.allOption) {
            this.options = this.options.filter((option) => option.value !== null);
            this.options.unshift({ displayValue: 'dropdown.all', value: null });
        }

        if (this.selectedValue !== null) {
            this.selectedOption = this.selectedValue;
        } else {
            this.selectedOption = this.options[0].value;
        }
    }

    public onValueChanged(target: EventTarget | null) {
        const value: string = (target as HTMLInputElement).value;
        this.valueChanged(value === 'null' ? null : value);
    }
}
