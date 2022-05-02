import { Component } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCalculator, faEarthAfrica, faSailboat, faUserGraduate} from '@fortawesome/free-solid-svg-icons';

interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    lineBefore?: boolean;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
    styleUrls: ['./profile-header.component.scss'],
    animations: [
        trigger('inOutAnimation', [
            transition(':enter', [
                style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 }),
                animate('0.25s ease-out', style({ transform: 'translateY(0) scaleY(1)', opacity: 1 })),
            ]),
            transition(':leave', [
                style({ transform: 'translateY(0) scaleY(1)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 })),
            ]),
        ]),
    ],
})
export class ProfileHeaderComponent {
    private static readonly ANIMATION_LENGTH_MILLISECONDS = 250;

    public menuOpen: boolean = false;
    public userName: string = 'Lord_Zmarfan';
    public displayName: string = 'Anonymous User';
    public menuItems: MenuItem[] = [
        { icon: faUserGraduate, translationKey: 'this.key.cool' },
        { icon: faEarthAfrica, translationKey: 'this.key.cool2' },
        { icon: faCalculator, translationKey: 'this.key.cool3' },
        { icon: faSailboat, translationKey: 'this.key.cool4' },
    ];

    private mLastTimeStamp: number = 0;

    public menuToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + ProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
            this.menuOpen = !this.menuOpen;
            this.mLastTimeStamp = event.timeStamp;
        }
    }
}
