import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class NavigationStateService {
    public static NAVIGATION_MENU_OPEN: boolean = false;
    public static PROFILE_MENU_OPEN: boolean = false;
}
