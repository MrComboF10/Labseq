import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LabseqService } from './labseq.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = signal('Labseq Calculator');

  inputN: number | null = null;
  n: number | null = null;
  result: string | null = null;
  loading = false;
  error: string | null = null;

  constructor(private labseqService: LabseqService) {}

  calculate() {
    if (this.inputN == null || this.inputN < 0) {
      this.error = 'Please enter a non-negative integer.';
      this.result = null;
      return;
    }

    this.error = null;
    this.loading = true;
    const currentN = this.inputN;

    this.labseqService.getValue(currentN).subscribe({
      next: (value) => {
        this.result = value;
        this.n = currentN;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error fetching value from backend.';
        console.error(err);
        this.loading = false;
      }
    });
  }
}

