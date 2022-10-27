import { Pipe, PipeTransform } from '@angular/core';
import {SessionType} from '../../../generated/server-responses';

@Pipe({
    name: 'sessionKey',
})
export class SessionKeyPipe implements PipeTransform {
    public transform(value: SessionType | undefined, ...args: unknown[]): string {
        switch (value) {
        case 'FIRST_PRACTICE': return 'homepage.nextRace.firstPractice';
        case 'SECOND_PRACTICE': return 'homepage.nextRace.secondPractice';
        case 'THIRD_PRACTICE': return 'homepage.nextRace.thirdPractice';
        case 'RACE': return 'homepage.nextRace.race';
        case 'QUALIFYING': return 'homepage.nextRace.qualifying';
        case 'SPRINT': return 'homepage.nextRace.sprint';
        default: return '';
        }
    }
}
