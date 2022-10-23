import { Component } from '@angular/core';

@Component({
    selector: 'app-next-race-times',
    templateUrl: './next-race-times.component.html',
    styleUrls: ['./next-race-times.component.scss'],
})
export class NextRaceTimesComponent {
    public sessions: number[] = [1, 2, 3, 4, 5];
}
