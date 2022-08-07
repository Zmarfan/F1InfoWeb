import {Component, Input, OnInit} from '@angular/core';

export interface DropdownOption {
    displayValue: string | number;
    value: string | number | null;
}

@Component({
    selector: 'app-drop-down-filter',
    templateUrl: './drop-down-filter.component.html',
    styleUrls: ['./drop-down-filter.component.scss'],
})
export class DropDownFilterComponent implements OnInit {
    @Input() public options: DropdownOption[] = [];
    @Input() public allOption: boolean = true;
    @Input() public labelKey: string = '';
    @Input() public valueChanged!: (value: any) => void;

    public selectedOption!: string | number | null;
    public uniqueLabel: string = `dropDownFilter${Math.random()}`;

    public ngOnInit() {
        if (this.allOption) {
            this.options.unshift({ displayValue: 'dropdown.all', value: null });
        }
        this.selectedOption = this.options[0].value;
    }

    public onValueChanged(target: EventTarget | null) {
        this.valueChanged((target as HTMLInputElement).value);
    }
}
