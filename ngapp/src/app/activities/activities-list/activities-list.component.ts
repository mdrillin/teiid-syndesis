/**
 * @license
 * Copyright 2017 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Activity } from "@activities/shared/activity.model";
import { Component } from "@angular/core";
import { EventEmitter } from "@angular/core";
import { Input } from "@angular/core";
import { Output } from "@angular/core";

@Component({
  moduleId: module.id,
  selector: "app-activities-list",
  templateUrl: "activities-list.component.html",
  styleUrls: ["activities-list.component.css"]
})
export class ActivitiesListComponent {

  @Input() private activities: Activity[];
  @Input() private selectedActivities: Activity[];
  @Output() private onActivitySelected: EventEmitter<Activity> = new EventEmitter<Activity>();
  @Output() private onActivityDeselected: EventEmitter<Activity> = new EventEmitter<Activity>();
  @Output() private onTagSelected: EventEmitter<string> = new EventEmitter<string>();
  @Output() private onEditActivity: EventEmitter<string> = new EventEmitter<string>();
  @Output() private onStartActivity: EventEmitter<string> = new EventEmitter<string>();
  @Output() private onDeleteActivity: EventEmitter<string> = new EventEmitter<string>();

  /**
   * Constructor.
   */
  constructor() {
    // nothing to do
  }

  public toggleActivitySelected(activity: Activity): void {
    if (this.isSelected(activity)) {
      this.onActivityDeselected.emit(activity);
    } else {
      this.onActivitySelected.emit(activity);
    }
  }

  public isSelected(activity: Activity): boolean {
    return this.selectedActivities.indexOf(activity) !== -1;
  }

  public startActivity(activityName: string): void {
    this.onStartActivity.emit(activityName);
  }

  public editActivity(activityName: string): void {
    this.onEditActivity.emit(activityName);
  }

  public deleteActivity(activityName: string): void {
    this.onDeleteActivity.emit(activityName);
  }

  public selectTag(tag: string, event: MouseEvent): void {
    event.stopPropagation();
    event.preventDefault();
    this.onTagSelected.emit(tag);
  }

}
