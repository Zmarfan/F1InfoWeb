import {Component, Input} from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faChevronDown, faChevronUp} from '@fortawesome/free-solid-svg-icons';
import {animate, keyframes, style, transition, trigger} from '@angular/animations';

@Component({
    selector: 'app-information-container',
    templateUrl: './information-container.component.html',
    styleUrls: ['./information-container.component.scss'],
    animations: [
        trigger('inOutAnimation', [
            transition(':enter', [
                animate('0.4s ease-in-out', keyframes([
                    style({ 'max-height': 0, opacity: '70%' }),
                    style({ 'max-height': '100vh', opacity: '100%' }),
                ])),
            ]),
            transition(':leave', [
                animate('0.4s ease-in-out', keyframes([
                    style({ 'max-height': '25vh', opacity: '100%' }),
                    style({ 'max-height': 0, opacity: 0 }),
                ])),
            ]),
        ]),
    ],
})
export class InformationContainerComponent {
    @Input() public headerKey!: string;
    @Input() public open: boolean = true;

    public get headerIcon(): IconDefinition {
        return this.open ? faChevronUp : faChevronDown;
    }

    public toggleOpenState() {
        this.open = !this.open;
    }
}
