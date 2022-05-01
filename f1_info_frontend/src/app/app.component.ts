import { Component } from '@angular/core';
import {faCoffee} from '@fortawesome/free-solid-svg-icons';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent {
    public title = 'f1_info_frontend';
    public icon: IconDefinition = faCoffee;
}
