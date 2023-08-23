import {ChangeDetectorRef, Component, Inject} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {MatDialog} from "@angular/material/dialog";
import {NavigationEnd, Router, RouterState} from "@angular/router";
import {DOCUMENT} from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Rock Paper Scissors Game';

  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;

  constructor(private router: Router, @Inject(DOCUMENT) private document: Document,
              changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, public dialog: MatDialog) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);

  }

}

