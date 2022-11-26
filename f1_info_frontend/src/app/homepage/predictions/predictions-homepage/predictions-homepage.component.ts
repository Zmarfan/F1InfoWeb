import { Component } from '@angular/core';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCheckCircle, faHourglassHalf} from '@fortawesome/free-solid-svg-icons';
import {parseTemplate} from 'url-template';
import {Endpoints} from '../../../configuration/endpoints';

@Component({
    selector: 'app-predictions-homepage',
    templateUrl: './predictions-homepage.component.html',
    styleUrls: ['./predictions-homepage.component.scss'],
})
export class PredictionsHomepageComponent {
    public previousRaces: number[] = [1, 2, 3];

    public get nextRacePredictionsIcon(): IconDefinition {
        return true ? faHourglassHalf : faCheckCircle;
    }

    public goToAllPredictions() {
        console.log('all');
    }

    public goToNextRacePredictions() {
        console.log('next');
    }

    public testFriendIcon(friendCode: string): string {
        return parseTemplate(Endpoints.FRIENDS.getProfileIcon).expand({ friendCode });
    }
}
