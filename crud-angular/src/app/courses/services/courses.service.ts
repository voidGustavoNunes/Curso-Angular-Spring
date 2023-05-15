import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Course } from '../model/course';
import { delay, first, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.API)
      .pipe(
        first(),
        //delay(5000),
        // tap(courses => console.log(courses))
      );
  }

  loadById(id: string) {
    return this.httpClient.get<Course>(`${this.API}/${id}`);
  }

  save(record: Partial<Course>, file?: File) {
    const formData = new FormData();
    if (file) {
      formData.append('image', file, file.name);
    }
    if (record._id) {
      return this.update(record, formData);
    }
    return this.create(record, formData);
  }

  private create(record: Partial<Course>, formData: FormData) {
    return this.httpClient.post<Course>(this.API, formData).pipe(first());
  }

  private update(record: Partial<Course>, formData: FormData) {
    return this.httpClient.put<Course>(`${this.API}/${record._id}`, formData).pipe(first());
  }

  remove(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}
