import styled from "styled-components";
import {TrashCanType} from "../types/TrashCanType.ts";
import DroppableTrashCan from "./DroppableTrashCan.tsx";

type TrashCanProps = {
    trashCans: TrashCanType [];
}

export default function TrashCan(props: Readonly<TrashCanProps>) {

    return (
        <StyledSection>
            {props.trashCans.map((trashCan) => (
                <DroppableTrashCan key={trashCan.id} trashCan={trashCan}/>
            ))
            }
        </StyledSection>
    )
}

const StyledSection = styled.section`
  position: absolute;
  width: 100%;
  height: 35vh;
  display: flex;
  bottom: 0;
  justify-content: space-evenly;
  align-items: center;
  gap: 5px;
`
