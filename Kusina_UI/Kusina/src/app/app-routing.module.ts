import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ErrorComponent } from './pages/error/error.component';
import { CustomComponent } from './pages/custom/custom.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { NotificationsComponent } from './pages/notifications/notifications.component';
import { LoginComponent } from './pages/login/login.component';
import { OnboardingComponent } from './pages/onboarding/onboarding.component';
import { CustomDashboardComponent } from './pages/custom-dashboard/custom-dashboard.component';
import { SuperAdminGuard } from './guards/super-admin.guard';
import { ProjectAdminGuard } from './guards/project-admin.guard';
import { OperationsAdminGuard } from './guards/operations-admin.guard';
import { AuthenticationGuard } from './guards/auth.guard';

export const routes: Routes = [
    // { path: '', redirectTo: 'dashboard',  pathMatch: 'full'},
    { path: '', component: LoginComponent},
    { path: 'onboarding', component: OnboardingComponent, canActivate: [AuthenticationGuard]},
    { path: 'custom-dashboard', component: CustomDashboardComponent, canActivate: [OperationsAdminGuard]},
    { path: 'dashboard', component: DashboardComponent, canActivate: [ProjectAdminGuard]},
    { path: 'custom', component: CustomComponent, canActivate: [ProjectAdminGuard] },
    { path: 'settings', component: SettingsComponent, canActivate: [SuperAdminGuard]},
    { path: 'error', component: ErrorComponent, canActivate: [ProjectAdminGuard] },
    { path: 'announcements', component: NotificationsComponent, canActivate: [ProjectAdminGuard]},
    { path: '**', component: NotFoundComponent, canActivate: [ProjectAdminGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
    providers: [SuperAdminGuard, ProjectAdminGuard, OperationsAdminGuard, AuthenticationGuard]
})

export class AppRoutingModule {

}
