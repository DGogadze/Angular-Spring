import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PostsComponent} from "./posts/posts.component";
import {ProfileComponent} from "./profile/profile.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {
    path: "posts",
    component: PostsComponent
  },
  {
    path: "profile",
    component: ProfileComponent
  },
  {
    path: "login",
    component: LoginComponent
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
