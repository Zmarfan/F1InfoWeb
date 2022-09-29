import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-information-container',
    templateUrl: './information-container.component.html',
    styleUrls: ['./information-container.component.scss'],
})
export class InformationContainerComponent {
    @Input() public headerKey!: string;
}
