// src/services/dataService.ts
interface FrequencyPoint {
    frequency: string;
    magnitude: number;
  }
  
  export const fetchFrequencyData = async (): Promise<FrequencyPoint[]> => {
    try {
      const response = await fetch('/api/v1/data/frequency');
      if (!response.ok) {
        throw new Error('Failed to fetch frequency data');
      }
      return await response.json();
    } catch (error) {
      throw new Error(`Error fetching frequency data: ${error}`);
    }
  };